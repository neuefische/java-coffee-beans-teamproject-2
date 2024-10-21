import PersonType from "./PersonType.tsx";

type MovieType = {
    id: string,
    name: string
    isWatched: boolean,
    rating: number,
    directors: PersonType[];
    actors: PersonType[]
}

export default MovieType;