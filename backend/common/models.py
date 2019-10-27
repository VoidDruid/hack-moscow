from pydantic import BaseModel


class ProvidedLocation(BaseModel):
    long: float
    lat: float
    duration: int
    city: str


class Category(BaseModel):
    title: str
    id: str


class CollectorLocation(BaseModel):
    long: float
    lat: float
    duration: int
    city: str
    title: str
    category: Category
