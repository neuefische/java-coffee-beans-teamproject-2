import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import RatingType from "../../../Type/RatingType.tsx";
import MovieType from "../../../Type/MovieType.tsx";
import PersonType from "../../../Type/PersonType.tsx";
import personType from "../../../Type/PersonType.tsx";

export default function MovieControls(
    {editModeEnabled, setEditModeEnabled, ratingData, movieData, actorData, directorData}:
        {
            editModeEnabled: boolean, setEditModeEnabled: (state: boolean) => void,
            ratingData: RatingType, movieData: MovieType, actorData: PersonType[],
            directorData: personType[]
        }) {

    const [saveButtonDisabled, setSaveButtonDisabled] = useState<boolean>(false);
    const [deleteButtonDisabled, setDeleteButtonDisabled] = useState<boolean>(false);

    const params = useParams();
    const id: string | undefined = params.id;

    const navigate = useNavigate();

    const save = async () => {
        setSaveButtonDisabled(true);
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

            const movieResponse = await axios.put(`/api/movie/${id}`, movieData);
            await axios.post(`/api/rating`, ratingData);
            for (const actor of actors) {
                const actorInstance = await actor;
                if (actorInstance.id) {
                    const data = {
                        movieId: movieResponse.data.id,
                        actorId: actorInstance.id
                    }
                    await axios.post<PersonType>(`/api/movie-actor`, data);
                }
            }

            for (const director of directors) {
                const directorInstance = await director;
                if (directorInstance.id) {
                    const data = {
                        movieId: movieResponse.data.id,
                        directorId: directorInstance.id
                    }
                    await axios.post<PersonType>(`/api/movie-director`, data);
                }
            }
        } catch (exception) {
            alert("Something went wrong");
            console.log(exception);
        } finally {
            setEditModeEnabled(false);
            setSaveButtonDisabled(false);
        }
    }

    const edit = () => {
        setEditModeEnabled(true);
    }

    const remove = async () => {
        setDeleteButtonDisabled(true);
        try {
            await axios.delete(`/api/movie/${id}`);
            await axios.delete(`/api/movie-actor/${id}`);
            await axios.delete(`/api/movie-director/${id}`);
            navigate("/");
        } catch (exception) {
            console.log(exception);
            alert("Something went wrong")
        } finally {
            setDeleteButtonDisabled(false);
        }
    }

    return (
        <div>
            <button onClick={save} hidden={!editModeEnabled} disabled={saveButtonDisabled}>Save</button>
            <button onClick={edit} hidden={editModeEnabled}>Edit</button>
            <button onClick={remove} disabled={deleteButtonDisabled}>Delete</button>
        </div>
    );
}