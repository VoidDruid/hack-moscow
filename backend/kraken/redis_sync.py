import logging
from redis import Redis

logger = logging.getLogger(__name__)

_missing = object()

REDIS_KEY_SEPARATOR = '|'
REDIS_CACHE_TTL = 24*60*60*90


def full_cache_key(prefix, key):
    return '{}{}{}'.format(prefix, REDIS_KEY_SEPARATOR, key)


class BaseRedisSyncStorage(object):
    def __init__(self, redis_conn, key_prefix, ttl=REDIS_CACHE_TTL):
        self.conn : Redis = redis_conn
        self.key_prefix = key_prefix
        self._ttl = ttl

    def lpush(self, name, values):
        self.conn.lpush(name, values)

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