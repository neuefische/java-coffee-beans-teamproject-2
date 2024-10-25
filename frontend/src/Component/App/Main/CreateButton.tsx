import axios from "axios";
import RatingType from "../../../Type/RatingType.tsx";
import MovieType from "../../../Type/MovieType.tsx";
import PersonType from "../../../Type/PersonType.tsx";
import personType from "../../../Type/PersonType.tsx";

export default function CreateButton(
    {
        ratingData, movieData, setIsUpdated,
        actorData, directorData
    }:
        {
            ratingData: RatingType, movieData: MovieType
            setIsUpdated: (state: boolean) => void,
            actorData: PersonType[],
            directorData: personType[]
        }) {

    const create = async function () {
        try {
            const actors = actorData.map(
                async (actor) => {
                    if (!actor.id) {
                        const response = await axios.post<PersonType>(`/api/actor`, actor);
                        actor.id = response.data.id;
                    }

                    return actor;
                }
            );

            const directors= directorData.map(
                async (director) => {
                    if (!director.id) {
                        const response = await axios.post<PersonType>(`/api/director`, director);
                        director.id = response.data.id;
                    }

                    return director;
                }
            );

            const movieResponse = await axios.post<MovieType>(`/api/movie`, movieData);
            ratingData.movieId = movieResponse.data.id;
            await axios.post(`/api/rating`, ratingData);

            for (const actor of actors) {
                const actorInstance = await actor;
                if (actorInstance.id) {
                    const data = {
                        movieId: movieResponse.data.id,
                        personId: actorInstance.id
                    }
                    await axios.post<PersonType>(`/api/movie-actor`, data);
                }
            }

            for (const director of directors) {
                const directorInstance = await director;
                if (directorInstance.id) {
                    const data = {
                        movieId: movieResponse.data.id,
                        personId: directorInstance.id
                    }
                    await axios.post<PersonType>(`/api/movie-director`, data);
                }
            }

            setIsUpdated(true);
        } catch {
            alert("Something went wrong");
        } finally {
            // TODO
        }

    }

    return (
        <button onClick={create}>Create</button>
    );
}