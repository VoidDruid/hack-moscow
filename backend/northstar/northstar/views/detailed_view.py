from rest_framework import generics

from northstar.serializers import *
from northstar.models import *


class UserDetailView(generics.RetrieveUpdateAPIView):
    """
        Get User by UID
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
    lookup_field = 'uid'


class EventDetailView(generics.RetrieveDestroyAPIView):
    """
        Get Event by ID
    """
    queryset = Event.objects.all()
    serializer_class = EventSerializer
    lookup_field = 'id'


class OrganisationDetailView(generics.RetrieveDestroyAPIView):
    """
        Get Organisation by ID
    """
    queryset = Organization.objects.all()
    serializer_class = OrganizationSerializer
    lookup_field = 'id'
