from __future__ import unicode_literals

from django.db import models

# Create your models here.

class Place(models.Model):
    name = models.CharField(verbose_name="Nombre", max_length=250)
    lat = models.FloatField()
    lng = models.FloatField()

    def __unicode__(self):
        return self.name

    def __str__(self):
        return self.name
