import logging
from redis import Redis

logger = logging.getLogger(__name__)

_missing = object()

REDIS_KEY_SEPARATOR = '|'
REDIS_CACHE_TTL = 24*60*60*90


def full_cache_key(prefix, key):
    separator = ''
    if prefix:
        separator = REDIS_KEY_SEPARATOR
    return '{}{}{}'.format(prefix, separator, key)


def strip_cache_key(key):
    return key.split(REDIS_KEY_SEPARATOR)[-1]


class BaseRedisSyncStorage(object):
    def __init__(self, redis_conn, key_prefix, ttl=REDIS_CACHE_TTL):
        self.conn: Redis = redis_conn
        self.key_prefix = key_prefix
        self._ttl = ttl

    def lpush(self, key, values):
        return self.conn.lpush(full_cache_key(self.key_prefix, key), values)

    def rpop(self, key):
        return self.conn.rpop(full_cache_key(self.key_prefix, key))

    def _get(self, key=None):
        return self.conn.get(full_cache_key(self.key_prefix, key))

    def get(self, key=None, default=_missing):
        value = self._get(key=key)

        if value is None:
            if default is _missing:
                raise KeyError
            return default

        return value

    def exists(self, key):
        return bool(self.conn.exists(full_cache_key(self.key_prefix, key)))

    def expire(self, key, ttl):
        return self.conn.expire(full_cache_key(self.key_prefix, key), ttl)

    def renew_expire(self, key):
        self.expire(key, self._ttl)

    def set(self, key=None, value=''):
        return self.conn.set(full_cache_key(self.key_prefix, key), value, ex=self._ttl)

    def set_if_absent(self, key=None, value=''):
        cache_key = full_cache_key(self.key_prefix, key)
        self.conn.set(cache_key, value, ex=self._ttl, nx=True)
        return self.conn.get(cache_key)

    def remove_multi(self, keys):
        if not keys:
            return []

        full_keys = [full_cache_key(self.key_prefix, key) for key in keys]
        return self.conn.delete(*full_keys)

    def remove(self, key):
        return self.remove_multi([key])

    def __iter__(self):
        yield from map(lambda key: strip_cache_key(key.decode('utf-8')), self.conn.scan_iter())
