package com.weatherstak.api.dto;

    public class ResponseCode {
        public int code;
        public String type;
        public String info;
        public boolean success;
        public Error error;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public Error getError(String code) {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }
    }



