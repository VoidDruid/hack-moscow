from rest_framework import generics

from northstar.serializers import UserSerializer, EventSerializer
from northstar.models import User, Event


class UserDetailView(generics.RetrieveAPIView):
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


