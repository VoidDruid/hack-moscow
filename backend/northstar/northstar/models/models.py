from django.contrib.postgres import fields as pg
from django.db import models
# from .validators import


class SexChoices:
    MALE = 'M'
    FEMALE = 'F'
    NONBINARY = 'NB'
    CHOICES = (
        (MALE, 'Male'),
        (FEMALE, 'Female'),
        (NONBINARY, 'Nonbinary')
    )


class EventType:
    PUBLIC = 'PB'
    PRIVATE = 'PR'
    CHOICES = (
        (PUBLIC, 'Public'),
        (PRIVATE, 'Private')
    )


class User(models.Model):
    uid = models.CharField(max_length=100, null=False, unique=True)
    sex = models.CharField(max_length=100, null=True, choices=SexChoices.CHOICES)
    age = models.IntegerField(null=True)


class Event(models.Model):
    title = models.CharField(max_length=100, null=False)
    description = models.CharField(max_length=250, null=True)
    type = models.CharField(max_length=100, null=True, choices=EventType.CHOICES)
    long = models.FloatField(null=False)
    lat = models.FloatField(null=False)
