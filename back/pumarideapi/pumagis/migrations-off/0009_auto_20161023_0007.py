# -*- coding: utf-8 -*-
# Generated by Django 1.10.1 on 2016-10-23 00:07
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('pumagis', '0008_remove_line_shortest_path'),
    ]

    operations = [
        migrations.AlterField(
            model_name='line',
            name='p_destino',
            field=models.CharField(default=b'(0.0,0.0)', max_length=50),
        ),
        migrations.AlterField(
            model_name='line',
            name='p_origen',
            field=models.CharField(default=b'(0.0,0.0)', max_length=50),
        ),
    ]