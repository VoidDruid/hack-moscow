import json
import logging
import time

from redis import Redis

from conf.settings import COLLECT_TIMEOUT

from common.redis_sync import BaseRedisSyncStorage
from common.models import ProvidedLocation
from northstar.models import User, UserCategory
from common.here_api import HERE

log = logging.getLogger(__name__)
redis = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='0'), '')
USER_LOCATIONS_PER_SCAN = 5


def loop():
    while True:
        try:
            collect()
        except Exception:
            log.exception('Caught exception in main collector thread!')
        time.sleep(COLLECT_TIMEOUT)


def collect():
    for key in redis:
        for _ in range(USER_LOCATIONS_PER_SCAN):
            value = redis.rpop(key)
            if not value:
                break
            process_value(key, value)


def process_value(key, value):
    try:
        location = ProvidedLocation(**json.loads(value))
        user_query = User.objects.filter(uid=key)
        if not user_query.exists():
            user = User(uid=key)
            user.save()
        else:
            user = user_query.first()
        place = HERE.get_category_by_location({
            'lat': location.lat,
            'long': location.long,
        })
        category = place['category']['id']
        user_category_query = UserCategory.objects.filter(
            user=user,
            category=category,
        )
        if not user_category_query.exists():
            user_category = UserCategory(user=user, category=category)
        else:
            user_category = user_category_query.first()
        user_category.points += 1
        user_category.save()
    except Exception:
        log.exception(f'Caught exception while collecting data for user {key}')
