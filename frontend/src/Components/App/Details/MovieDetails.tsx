import {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

export default function MovieDetails() {
    type PersonData = {
        id: string,
        name: string
    }

    type MovieData = {
        id: string,
        name: string
        isWatched: boolean,
        rating: number,
        directors: PersonData[];
        actors: PersonData[]
    }

    const {id} = useParams();

    const [movieData, setMovieData] = useState<MovieData>();

    const updateMovieData = function () {
        axios.get<MovieData>("/api/movie/" + id).then(
            (result) => setMovieData(result.data)
        ).catch(
            () => console.log("Something went wrong")
        )
    }

    useEffect(updateMovieData, []);

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
                <input type="textarea" name="directors" value={movieData?.directors?.join(', ')} disabled/>
                <label htmlFor="actors">Starring</label>
                <input type="textarea" name="actors" value={movieData?.actors?.join(', ')} disabled/>
            </form>
        </div>
    );
}