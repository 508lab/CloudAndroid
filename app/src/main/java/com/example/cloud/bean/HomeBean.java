package com.example.cloud.bean;

import java.util.List;

public class HomeBean {

    /**
     * status : 200
     * data : {"d":[{"name":"aaa"},{"name":"bbb"}],"f":[{"name":"index.md"}]}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DBean> d;
        private List<FBean> f;

        public List<DBean> getD() {
            return d;
        }

        public void setD(List<DBean> d) {
            this.d = d;
        }

        public List<FBean> getF() {
            return f;
        }

        public void setF(List<FBean> f) {
            this.f = f;
        }

        public static class DBean {
            /**
             * name : aaa
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class FBean {
            /**
             * name : index.md
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
