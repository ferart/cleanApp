package com.example.data.failures

import com.example.domain.exceptions.Failure

class DataFailures {
    class ListNotAvailable: Failure.FeatureFailure()
}