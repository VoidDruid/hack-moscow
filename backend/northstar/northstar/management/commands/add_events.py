import random
import string

from django.core.management.base import BaseCommand

from northstar.models import Event, Organization, EventType


class Command(BaseCommand):

    def handle(self, *args, **options):
        list_of_organisations = Organization.objects.all().values('id')
        for _ in range(100):
            organization_id = random.choice(list_of_organisations)['id']
            title = "title"
            description = "description"
            type = random.choice([EventType.PRIVATE, EventType.PUBLIC])
            org = Organization.objects.filter(id=organization_id).first()
            long = org.long
            lat = org.lat
            Event(organization=org, title=title, description=description,
                  type=type, long=long, lat=lat).save()
