from pydantic import BaseModel


class ProvidedLocation(BaseModel):
    long: float
    lat: float
    duration: int
