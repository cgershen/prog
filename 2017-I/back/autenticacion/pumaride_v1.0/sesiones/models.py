from django.db import models
from authemail2.models import EmailUserManager, EmailAbstractUser

class PumaUsuario(EmailAbstractUser):
    # Custom fields
    fecha_de_nacimiento = models.DateField('Date of birth', null=True,
        blank=True)

    # Required
    objects = EmailUserManager()
