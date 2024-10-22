import {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import PersonList from "./MovieDetails/PersonList.tsx";
import MovieType from "../../../Type/MovieType.tsx";
import PersonType from "../../../Type/PersonType.tsx";
import "../../../Style/Details.css"

export default function MovieDetails() {
    const errorMessage = "Something went wrong";

    const {id} = useParams();
    const [movieData, setMovieData] = useState<MovieType>();
    const [actorsData, setActorsData] = useState<PersonType[]>([]);
    const [directorsData, setDirectorsData] = useState<PersonType[]>([]);

    const updateMovieData = function () {
        axios.get<MovieType>("/api/movie/" + id).then(
            (result) => setMovieData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateActorsData = function () {
        axios.get<PersonType[]>("/api/movie-actor/" + id).then(
            (result) => setActorsData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateDirectorsData = function () {
        axios.get<PersonType[]>("/api/movie-director/" + id).then(
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
                {directorsData && <PersoncdList people={directorsData} legend={"Directed by"}/>}
                {actorsData && <PersonList people={actorsData} legend={"Starring"}/>}
            </form>
        </div>
    );
}