import ListElement from "./MovieList/ListElement.tsx";
import MovieType from "../../../../Type/MovieType.tsx";

export default function MovieList({ data }: { data: MovieType[] }) {
    return (
        <div>
            MovieList
            <div>
                {data?.map(
                    (movie) => <ListElement key={movie.id} movie={movie} />
                )}
            </div>
        </div>
    );
}
