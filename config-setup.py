
from digitalnx_web.api.database import init_db
from digitalnx_web.api.database import db_session
from digitalnx_web.api import models
from digitalnx_web.api.enums import SecurityMode, UILanguage
from digitalnx_web.api.models import Settings

init_db()

settings = Settings('0.1.2', SecurityMode.NORMAL, UILanguage.EN)
db_session.add(settings)
db_session.commit()