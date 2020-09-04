import { useState, useEffect } from "react";
import axios from 'axios';

/*
 * https://medium.com/better-programming/learn-to-create-your-own-usefetch-react-hook-9cc31b038e53
 * https://wanago.io/2019/11/18/useeffect-hook-in-react-custom-hooks
 */

/**
 *   Make makeUseFetch returns a function to actually make the API call.
 *
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
        const res = await axios({ ...options, url });
        const json = res.data;
        if (res.status != 200) {
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
export const Api = {
  login: {
    post: (formData) =>
      makeUseFetch("http://localhost:8080/login", {
        method: "POST",
        data: formData,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        withCredentials: true
      }),
  },
  Signup: {
    post: (jsonData) =>
      makeUseFetch("http://localhost:8080/api/user/createCustomer", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        data: jsonData,
      }),
  },
};
