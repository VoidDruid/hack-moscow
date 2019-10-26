from rest_framework import generics

from northstar.serializers import UserSerializer, EventSerializer
from northstar.models import User


class UserDetailView(generics.RetrieveAPIView):
    """
        Get User by UID
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
    lookup_field = 'uid'


class EventDetailView(generics.RetrieveAPIView):
    """
        Get User by UID
    """
    queryset = User.objects.all()
    serializer_class = EventSerializer
    lookup_field = 'id'


