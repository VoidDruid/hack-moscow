import os

TRUE_ENV_VALUES = (True, 'true', 'True', '1')


def getenv_bool(name, default=None):
    value = os.getenv(name, default)
    if value in TRUE_ENV_VALUES:
        return True
    else:
        return False
