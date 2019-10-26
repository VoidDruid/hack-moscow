import random
import string

from django.core.management.base import BaseCommand

from northstar.models import Event, Organization, EventType

bag_of_words = ['Our', 'After', 'Party', 'Happy', 'Day', 'Discounts', 'Only', 'Today', 'Nice']
description_pull = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum'.split()


class Command(BaseCommand):

    def handle(self, *args, **options):
        list_of_organisations = Organization.objects.all().values('id')
        for _ in range(100):
            organization_id = random.choice(list_of_organisations)['id']
            title = ' '.join(random.choices(bag_of_words, k=3))
            description = ' '.join(random.choices(description_pull, k=5))
            type = random.choice([EventType.PRIVATE, EventType.PUBLIC])
            org = Organization.objects.filter(id=organization_id).first()
            Event(
                organization=org,
                title=title,
                description=description,
                type=type,
                long=org.long,
                lat=org.lat
            ).save()
