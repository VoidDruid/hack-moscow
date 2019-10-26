from rest_framework import generics

from northstar.serializers import UserSerializer
from northstar.models import User


class UserDetailView(generics.RetrieveAPIView):
    """
        Get User by UID
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
    lookup_field = 'uid'


class UserDetailView(generics.RetrieveAPIView):
    """
        Get User by UID
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
    lookup_field = 'uid'
