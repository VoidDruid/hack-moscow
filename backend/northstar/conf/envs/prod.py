import os

SECRET_KEY = os.getenv('SECRET_KEY')
DATABASE_NAME = 'northstar'
DATABASE_PORT = '5432'
DATABASE_USER = ''
DATABASE_PASSWORD = ''
DATABASE_HOST = ''

HERE_APP_ID = os.getenv('HERE_APP_ID')
HERE_APP_CODE = os.getenv('HERE_APP_CODE')

if not HERE_APP_CODE or not HERE_APP_ID:
    raise ValueError('Provide HERE app id and code')
