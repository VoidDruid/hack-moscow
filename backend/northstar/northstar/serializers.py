from rest_framework import serializers
from northstar.models import *


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'uid', 'sex', 'age')


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = ('id', 'title', 'type', 'description', 'long', 'lat', 'organization')


class OrganizationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Organization
        fields = ('id', 'login', 'password', 'address', 'category', 'long', 'lat', 'title')