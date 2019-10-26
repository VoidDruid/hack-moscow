import json
import logging
import time

from redis import Redis

from conf.settings import COLLECT_TIMEOUT

from common.redis_sync import BaseRedisSyncStorage
from common.models import CollectorLocation
from northstar.models import User, UserCategory

log = logging.getLogger(__name__)

redis = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='0'), '')
redis_cities = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='1'), '')

CITY_DATA_SEP = '|||'
USER_LOCATIONS_PER_SCAN = 5


def loop():
    while True:
        try:
            print('Running collection')
            collect(redis, process_user_data)
        except Exception as e:
            print(e)
            log.exception('Caught exception in main collector thread!')
        time.sleep(COLLECT_TIMEOUT)


def collect(redis_connector, processor):
    for key in redis:
        processor(key, redis_connector)


def process_user_data(key, redis_connector):
    for _ in range(USER_LOCATIONS_PER_SCAN):
        print(f'Processing key {key}')
        value = redis_connector.rpop(key)
        if not value:
            break
        process_value(key, value)


def process_value(key, value):
    try:
        location = CollectorLocation(**json.loads(value))

        counter = redis_cities.hget(location.city, location.title)
        if counter is None:
            counter = 1
        else:
            counter = int(counter) + 1
        redis_cities.hset(location.city, location.title, counter)
        redis_cities.hset(location.city, f'{location.title}{CITY_DATA_SEP}lat', location.lat)
        redis_cities.hset(location.city, f'{location.title}{CITY_DATA_SEP}long', location.long)

        user_query = User.objects.filter(uid=key)
        if not user_query.exists():
            user = User(uid=key)
            user.save()
        else:
            user = user_query.first()

        user_category_query = UserCategory.objects.filter(
            user=user,
            category=location.category.id,
        )
        if not user_category_query.exists():
            user_category = UserCategory(user=user, category=location.category.id)
        else:
            user_category = user_category_query.first()

        user_category.points += 1
        user_category.save()
    except Exception as e:
        print(e)
        log.exception(f'Caught exception while collecting data for user {key}')
