import { useState, useEffect } from "react";

/*
 * https://medium.com/better-programming/learn-to-create-your-own-usefetch-react-hook-9cc31b038e53
 * https://wanago.io/2019/11/18/useeffect-hook-in-react-custom-hooks
 */
export const makeUseFetch = (url, options) => (callback = undefined) => {
    const [response, setResponse] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        const abortController = new AbortController();
        const signal = abortController.signal;
        const doFetch = async () => {
            setLoading(true);
            try {
                const res = await fetch(url, options);
                const json = await res.json();
                if (!res.ok) {
                    throw json;
                }
                if (!signal.aborted) {
                    if (callback !== undefined) {
                        callback(json);
                    }
                    setResponse(json);
                }
            } catch (e) {
                if (!signal.aborted) {
                    setError(e);
                }
            } finally {
                if (!signal.aborted) {
                    setLoading(false);
                }
            }
        };
        doFetch();
        return () => {
            abortController.abort();
        };
    }, [callback]);
    return { response, error, loading };
};
const GET_SAME_ORIGIN_JSON = {
    method: "GET",
    credentials: "same-origin",
    headers: { "Content-Type": "application/json" },
    redirect: "manual",
};
export const useAPI = {
    login: {
        post: (jsonData) =>
            makeUseFetch("http://localhost:8080/api/user/find", {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(jsonData)
            })
    },
    voice: {
        post: (formData) =>
            makeUseFetch("/api/voice", {
                headers: { Accept: "application/json" },
                method: "POST",
                body: formData,
            }),
        ping: makeUseFetch("api/voice/ping", GET_SAME_ORIGIN_JSON),
    },
    search_data: {
        get: (selectOptionsURI) =>
            makeUseFetch(selectOptionsURI, GET_SAME_ORIGIN_JSON),
    },
};
