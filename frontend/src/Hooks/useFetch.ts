import {useEffect, useState} from "react";

export type UseFetchStateType = "loading" | "done" | "fail";

export const useFetch = <T>(url: string) => {
    type FetchDataType = {
        data: T[];
        state: UseFetchStateType;
    };

    const [fetchData, setFetchData] = useState<FetchDataType>({
        data: [],
        state: "loading",
    });

    useEffect(() => {
        fetch(url)
            .then((res) => res.json())
            .then((data: T[]) => {
                setFetchData({data, state: "done"})
            })
            .catch(() => setFetchData({data: [], state: "fail"}));
    }, [url]);

    return fetchData;
};
