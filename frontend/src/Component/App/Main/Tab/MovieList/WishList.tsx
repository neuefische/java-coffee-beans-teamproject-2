import ListElement from "./ListElement.tsx";
import MovieRatingType from "../../../../../Type/MovieRatingType.tsx";
import ListHeaders from "../../ListHeaders.tsx";

export default function WishList({data}: { data: MovieRatingType[] }) {
    return (
        <div>
            <ListHeaders/>
            <div>
                {data?.map(
                    (movie) => <ListElement key={movie.movieId} movie={movie}/>
                )}
            </div>
        </div>
    );
}
