# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2016-12-03 03:45
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('pumagis', '0013_remove_line__shortest_path'),
    ]

    operations = [
        migrations.AddField(
            model_name='line',
            name='_shortest_path',
            field=models.TextField(default=b''),
        ),
    ]