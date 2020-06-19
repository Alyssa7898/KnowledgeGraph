package com.core.entity;

import java.util.ArrayList;
import java.util.List;

public class CKnowledgeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CKnowledgeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andKnowledgeIsNull() {
            addCriterion("Knowledge is null");
            return (Criteria) this;
        }

        public Criteria andKnowledgeIsNotNull() {
            addCriterion("Knowledge is not null");
            return (Criteria) this;
        }

        public Criteria andKnowledgeEqualTo(String value) {
            addCriterion("Knowledge =", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotEqualTo(String value) {
            addCriterion("Knowledge <>", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeGreaterThan(String value) {
            addCriterion("Knowledge >", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeGreaterThanOrEqualTo(String value) {
            addCriterion("Knowledge >=", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeLessThan(String value) {
            addCriterion("Knowledge <", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeLessThanOrEqualTo(String value) {
            addCriterion("Knowledge <=", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeLike(String value) {
            addCriterion("Knowledge like", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotLike(String value) {
            addCriterion("Knowledge not like", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeIn(List<String> values) {
            addCriterion("Knowledge in", values, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotIn(List<String> values) {
            addCriterion("Knowledge not in", values, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeBetween(String value1, String value2) {
            addCriterion("Knowledge between", value1, value2, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotBetween(String value1, String value2) {
            addCriterion("Knowledge not between", value1, value2, "knowledge");
            return (Criteria) this;
        }

        public Criteria andContenttextIsNull() {
            addCriterion("ContentText is null");
            return (Criteria) this;
        }

        public Criteria andContenttextIsNotNull() {
            addCriterion("ContentText is not null");
            return (Criteria) this;
        }

        public Criteria andContenttextEqualTo(String value) {
            addCriterion("ContentText =", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextNotEqualTo(String value) {
            addCriterion("ContentText <>", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextGreaterThan(String value) {
            addCriterion("ContentText >", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextGreaterThanOrEqualTo(String value) {
            addCriterion("ContentText >=", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextLessThan(String value) {
            addCriterion("ContentText <", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextLessThanOrEqualTo(String value) {
            addCriterion("ContentText <=", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextLike(String value) {
            addCriterion("ContentText like", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextNotLike(String value) {
            addCriterion("ContentText not like", value, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextIn(List<String> values) {
            addCriterion("ContentText in", values, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextNotIn(List<String> values) {
            addCriterion("ContentText not in", values, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextBetween(String value1, String value2) {
            addCriterion("ContentText between", value1, value2, "contenttext");
            return (Criteria) this;
        }

        public Criteria andContenttextNotBetween(String value1, String value2) {
            addCriterion("ContentText not between", value1, value2, "contenttext");
            return (Criteria) this;
        }

        public Criteria andVideoIsNull() {
            addCriterion("Video is null");
            return (Criteria) this;
        }

        public Criteria andVideoIsNotNull() {
            addCriterion("Video is not null");
            return (Criteria) this;
        }

        public Criteria andVideoEqualTo(String value) {
            addCriterion("Video =", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotEqualTo(String value) {
            addCriterion("Video <>", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThan(String value) {
            addCriterion("Video >", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThanOrEqualTo(String value) {
            addCriterion("Video >=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThan(String value) {
            addCriterion("Video <", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThanOrEqualTo(String value) {
            addCriterion("Video <=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLike(String value) {
            addCriterion("Video like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotLike(String value) {
            addCriterion("Video not like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoIn(List<String> values) {
            addCriterion("Video in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotIn(List<String> values) {
            addCriterion("Video not in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoBetween(String value1, String value2) {
            addCriterion("Video between", value1, value2, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotBetween(String value1, String value2) {
            addCriterion("Video not between", value1, value2, "video");
            return (Criteria) this;
        }

        public Criteria andLinkIsNull() {
            addCriterion("Link is null");
            return (Criteria) this;
        }

        public Criteria andLinkIsNotNull() {
            addCriterion("Link is not null");
            return (Criteria) this;
        }

        public Criteria andLinkEqualTo(String value) {
            addCriterion("Link =", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotEqualTo(String value) {
            addCriterion("Link <>", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThan(String value) {
            addCriterion("Link >", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThanOrEqualTo(String value) {
            addCriterion("Link >=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThan(String value) {
            addCriterion("Link <", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThanOrEqualTo(String value) {
            addCriterion("Link <=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLike(String value) {
            addCriterion("Link like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotLike(String value) {
            addCriterion("Link not like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkIn(List<String> values) {
            addCriterion("Link in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotIn(List<String> values) {
            addCriterion("Link not in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkBetween(String value1, String value2) {
            addCriterion("Link between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotBetween(String value1, String value2) {
            addCriterion("Link not between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andExerciseIsNull() {
            addCriterion("Exercise is null");
            return (Criteria) this;
        }

        public Criteria andExerciseIsNotNull() {
            addCriterion("Exercise is not null");
            return (Criteria) this;
        }

        public Criteria andExerciseEqualTo(String value) {
            addCriterion("Exercise =", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseNotEqualTo(String value) {
            addCriterion("Exercise <>", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseGreaterThan(String value) {
            addCriterion("Exercise >", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseGreaterThanOrEqualTo(String value) {
            addCriterion("Exercise >=", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseLessThan(String value) {
            addCriterion("Exercise <", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseLessThanOrEqualTo(String value) {
            addCriterion("Exercise <=", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseLike(String value) {
            addCriterion("Exercise like", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseNotLike(String value) {
            addCriterion("Exercise not like", value, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseIn(List<String> values) {
            addCriterion("Exercise in", values, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseNotIn(List<String> values) {
            addCriterion("Exercise not in", values, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseBetween(String value1, String value2) {
            addCriterion("Exercise between", value1, value2, "exercise");
            return (Criteria) this;
        }

        public Criteria andExerciseNotBetween(String value1, String value2) {
            addCriterion("Exercise not between", value1, value2, "exercise");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}