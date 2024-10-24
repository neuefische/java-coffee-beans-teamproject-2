import axios from "axios";
import RatingType from "../../../Type/RatingType.tsx";
import MovieType from "../../../Type/MovieType.tsx";

export default function CreateButton(
    {ratingData, movieData, setIsUpdated}:
        {
            ratingData: RatingType, movieData: MovieType
            setIsUpdated: (state: boolean) => void
        }) {

    const create = async function () {
        try {
            const response = await axios.post<MovieType>(`/api/movie`, movieData);
            ratingData.movieId = response.data.id;
            await axios.post(`/api/rating`, ratingData);
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