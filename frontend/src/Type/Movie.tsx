import Person from "./Person.tsx";

type Movie = {
    id: string,
    name: string
    isWatched: boolean,
    rating: number,
    directors: Person[];
    actors: Person[]
}

export default Movie;