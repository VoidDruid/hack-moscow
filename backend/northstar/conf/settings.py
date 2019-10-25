import os

from conf.envs.env_utils import getenv_bool


class Level:
    LOCAL = 'local'
    DEV = 'dev'
    PROD = 'prod'


RUN_LEVEL = os.getenv('RUN_LEVEL', Level.LOCAL)

from conf.envs.prod import *
if RUN_LEVEL != Level.PROD:  # if not prod - import dev config
    from conf.envs.dev import *
if RUN_LEVEL != Level.DEV:  # if not even dev - import local config
    from conf.envs.local import *

if not SECRET_KEY:
    raise LookupError('Provide SECRET_KEY env variable')

DEBUG = getenv_bool('DEBUG', (RUN_LEVEL != Level.PROD))
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Application definition
INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'rest_framework',
    'drf_yasg',
    'django_extensions',
    'corsheaders',
    'northstar',
]

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

CORS_ORIGIN_ALLOW_ALL = True
CORS_ALLOW_CREDENTIALS = False

ROOT_URLCONF = 'conf.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'conf.wsgi.application'

USE_POSTGRES = getenv_bool('USE_POSTGRES', RUN_LEVEL != Level.LOCAL)
# Database configuration
database_conf = {
    'ENGINE': 'django.db.backends.postgresql' if USE_POSTGRES else 'django.db.backends.sqlite3',
    'NAME': os.getenv('DB_NAME', DATABASE_NAME),
}
if USE_POSTGRES:
    database_conf = {
        **database_conf,
        'USER': os.getenv('DB_USER', DATABASE_USER),
        'PASSWORD': os.getenv('DB_PASSWORD', DATABASE_PASSWORD),
        'HOST': os.getenv('DB_HOST', DATABASE_HOST),
        'PORT': os.getenv('DB_PORT', DATABASE_PORT)
    }
DATABASES = {
    'default': database_conf
}

# Password validation
AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]

# Internationalization
LANGUAGE_CODE = 'ru-RU'
TIME_ZONE = 'Europe/Moscow'
USE_I18N = True
USE_L10N = True
USE_TZ = True

# Static files (CSS, JavaScript, Images)
STATIC_URL = '/static/'
STATIC_ROOT = os.path.join(BASE_DIR, 'static')
