import {useFetch} from "../../../Hooks/useFetch.ts";
import WishList from "./Tab/MovieList/WishList.tsx";
import MovieRatingType from "../../../Type/MovieRatingType.tsx";

export default function WishlistTab() {
    const {data, state} = useFetch<MovieRatingType>("http://localhost:5173/api/movie/wishlist");
    if (state === "loading") {
        return <div>Loading...</div>;
    }

    return (
        <div className={"wishlistTab"}>
            <h2>To be watched movies:</h2>
            <WishList data={data}/>
        </div>
    );
}