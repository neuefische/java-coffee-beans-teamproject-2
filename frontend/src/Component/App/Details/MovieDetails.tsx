import {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import PersonList from "./MovieDetails/PersonList.tsx";
import Movie from "../../../Type/Movie.tsx";
import Person from "../../../Type/Person.tsx";

export default function MovieDetails() {
    const errorMessage = "Something went wrong";

    const {id} = useParams();
    const [movieData, setMovieData] = useState<Movie>();
    const [actorsData, setActorsData] = useState<Person[]>([]);
    const [directorsData, setDirectorsData] = useState<Person[]>([]);

    const updateMovieData = function () {
        axios.get<Movie>("/api/movie/" + id).then(
            (result) => setMovieData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateActorsData = function () {
        axios.get<Person[]>("/api/movie-actor/" + id).then(
            (result) => setActorsData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateDirectorsData = function () {
        axios.get<Person[]>("/api/movie-director/" + id).then(
            (result) => setDirectorsData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }

    useEffect(updateMovieData, [id]);
    useEffect(updateActorsData, [id]);
    useEffect(updateDirectorsData, [id]);

    return (
        <div className="movie-details">
            <form>
                <label htmlFor="title">Title</label>
                <input type="text" name="title" value={movieData?.name} disabled/>
                <label htmlFor="is-watched">Is watched</label>
                <input type="text" name="is-watched" value={String(movieData?.isWatched)} disabled/>
                <label htmlFor="rating">Rating</label>
                <input type="text" name="rating" value={movieData?.rating} disabled/>
                <label htmlFor="directors">Directed by</label>
                {directorsData && <PersonList data={directorsData}/>}
                <label htmlFor="actors">Starring</label>
                {actorsData && <PersonList data={actorsData}/>}
            </form>
        </div>
    );
}