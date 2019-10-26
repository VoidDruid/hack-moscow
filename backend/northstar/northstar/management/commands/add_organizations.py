import random
import string

from django.core.management.base import BaseCommand

from northstar.models import Organization


class Command(BaseCommand):
    @staticmethod
    def login_generator():
        return ''.join(random.choice(string.ascii_letters) for _ in range(10))

    @staticmethod
    def password_generator():
        return ''.join(random.choice(string.hexdigits) for _ in range(25))

    def handle(self, *args, **options):
        for _ in range(100):
            sex = random.choice(self.list_of_sex)
            age = random.choice(self.list_of_ages)
            uid = self.uid_generator()
            User(uid=uid, sex=sex, age=age).save()
