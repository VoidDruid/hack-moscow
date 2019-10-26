import random
import string

from django.core.management.base import BaseCommand

from northstar.models import User


class Command(BaseCommand):
    list_of_sex = ['M'] * 15 + ['F'] * 10 + ['NB'] * 5
    list_of_ages = []
    list_of_ages += (list(range(16, 20)) * 7)
    list_of_ages += (list(range(21, 35)) * 15)
    list_of_ages += (list(range(35, 55)) * 8)

    @staticmethod
    def uid_generator():
        return ''.join(random.choice(string.digits) for _ in range(24))

    def handle(self, *args, **options):
        for _ in range(100):
            sex = random.choice(self.list_of_sex)
            age = random.choice(self.list_of_ages)
            uid = self.uid_generator()
            User(uid=uid, sex=sex, age=age).save()
