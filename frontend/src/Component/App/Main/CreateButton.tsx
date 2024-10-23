import axios from "axios";
import RatingType from "../../../Type/RatingType.tsx";
import MovieType from "../../../Type/MovieType.tsx";

export default function CreateButton(
    {ratingData, movieData}:
        {
            ratingData: RatingType, movieData: MovieType
        }) {

    const create = async function () {
        try {
            const response = await axios.post<MovieType>(`/api/movie`, movieData);
            ratingData.movieId = response.data.id;
            await axios.post(`/api/rating`, ratingData);
        } catch {
            alert("Something went wrong");
        } finally {
            // TODO
        }
    }

    return (
        <div>
            <button onClick={create}>Create</button>
        </div>
    );
}