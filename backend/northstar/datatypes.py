from rest_framework import serializers


class Location(serializers.Serializer):
    long = serializers.FloatField()
    lat = serializers.FloatField()

    def create(self, validated_data):
        pass

    def update(self, instance, validated_data):
        pass


class Place(serializers.Serializer):
    location = Location()
    title = serializers.CharField(max_length=100)
    category = serializers.CharField(max_length=100)

    def create(self, validated_data):
        pass

    def update(self, instance, validated_data):
        pass

