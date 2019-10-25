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


class User(models.Model):
    uid = models.CharField(max_length=100, null=False)
    sex = models.CharField(max_length=100, null=True, choices=SexChoices.CHOICES)
    age = models.IntegerField(null=True)
