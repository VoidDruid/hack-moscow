import random
import string

from django.core.management.base import BaseCommand

from northstar.models import Organization
from .organizations import new_places


class Command(BaseCommand):
    @staticmethod
    def login_generator():
        return ''.join(random.choice(string.ascii_letters) for _ in range(10))

    @staticmethod
    def password_generator():
        return (''.join(random.choice(string.hexdigits) for _ in range(25))).encode()

    def handle(self, *args, **options):
        for pl in new_places:
            Organization(login=self.login_generator(), password=self.password_generator(), address="address",
                         category=pl['category'], long=pl['location']['long'],
                         lat=pl['location']['lat'],
                         title=pl['title']).save()
