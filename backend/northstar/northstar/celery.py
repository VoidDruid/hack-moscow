import os

from celery import Celery

import conf.settings as settings

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'conf.settings')

app = Celery('celery')
app.config_from_object(settings, namespace='CELERY')

# celery worker -A northstar