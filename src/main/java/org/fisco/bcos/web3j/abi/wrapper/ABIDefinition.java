package org.fisco.bcos.web3j.abi.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.fisco.bcos.web3j.abi.FunctionEncoder;

/**
 * ABIDefinition wrapper
 *
 * <p>Link https://solidity.readthedocs.io/en/develop/abi-spec.html#json <br>
 * type: "function", "constructor", "receive" (the "receive Ether" function) or "fallback" (the
 * "default" function); <br>
 * name: the name of the function; <br>
 * inputs: an array of objects, each of which contains: <br>
 * name: the name of the parameter. <br>
 * type: the canonical type of the parameter (more below). <br>
 * components: used for tuple types (more below). <br>
 * outputs: an array of objects similar to inputs. <br>
 * stateMutability: a string with one of the following values: pure (specified to not read
 * blockchain state), view (specified to not modify the blockchain state), nonpayable (function does
 * not accept Ether - the default) and payable (function accepts Ether). <br>
 */
public class ABIDefinition {
    private String name;
    private String type;
    private boolean constant;
    private boolean payable;
    private boolean anonymous;
    private String stateMutability;

    private List<NamedType> inputs;
    private List<NamedType> outputs;

    public ABIDefinition() {}

    public ABIDefinition(
            String name,
            String type,
            boolean constant,
            boolean payable,
            boolean anonymous,
            String stateMutability) {
        this.name = name;
        this.type = type;
        this.constant = constant;
        this.payable = payable;
        this.anonymous = anonymous;
        this.stateMutability = stateMutability;
    }

    public ABIDefinition(
            boolean constant,
            List<NamedType> inputs,
            String name,
            List<NamedType> outputs,
            String type,
            boolean payable) {
        this(constant, inputs, name, outputs, type, payable, null);
    }

    public ABIDefinition(
            boolean constant,
            List<NamedType> inputs,
            String name,
            List<NamedType> outputs,
            String type,
            boolean payable,
            String stateMutability) {
        this.constant = constant;
        this.inputs = inputs;
        this.name = name;
        this.outputs = outputs;
        this.type = type;
        this.payable = payable;
        this.stateMutability = stateMutability;
    }

    /**
     * string method signature
     *
     * @return
     */
    public String getMethodSignatureAsString() {
        StringBuilder result = new StringBuilder();
        result.append(name);
        result.append("(");
        String params =
                getInputs()
                        .stream()
                        .map(abi -> abi.getTypeAsString())
                        .collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        return result.toString();
    }

    /**
     * method id
     *
     * @return
     */
    public String getMethodId() {
        return FunctionEncoder.buildMethodId(getMethodSignatureAsString());
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public List<NamedType> getInputs() {
        return inputs;
    }

    public void setInputs(List<NamedType> inputs) {
        this.inputs = inputs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NamedType> getOutputs() {
        return outputs;
    }

    public boolean hasOutputs() {
        return !outputs.isEmpty();
    }

    public void setOutputs(List<NamedType> outputs) {
        this.outputs = outputs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public String getStateMutability() {
        return stateMutability;
    }

    public void setStateMutability(String stateMutability) {
        this.stateMutability = stateMutability;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ABIDefinition)) {
            return false;
        }

        ABIDefinition that = (ABIDefinition) o;

        if (isConstant() != that.isConstant()) {
            return false;
        }
        if (isPayable() != that.isPayable()) {
            return false;
        }
        if (getInputs() != null
                ? !getInputs().equals(that.getInputs())
                : that.getInputs() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
            return false;
        }
        if (getOutputs() != null
                ? !getOutputs().equals(that.getOutputs())
                : that.getOutputs() != null) {
            return false;
        }
        if (getStateMutability() != null
                ? !getStateMutability().equals(that.getStateMutability())
                : that.getStateMutability() != null) {
            return false;
        }
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = (isConstant() ? 1 : 0);
        result = 31 * result + (getInputs() != null ? getInputs().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getOutputs() != null ? getOutputs().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (isPayable() ? 1 : 0);
        result = 31 * result + (getStateMutability() != null ? getStateMutability().hashCode() : 0);
        return result;
    }

    public static class Type {
        public String type;
        public String rawType;
        public List<Integer> dimensions = new ArrayList<Integer>();

        public Type(String name) {
            int index = name.indexOf('[');
            this.rawType = (-1 == index) ? name.trim() : name.substring(0, index);
            this.type = name;
            this.initialize();
        }

        private void initialize() {
            Pattern p = Pattern.compile("\\[[0-9]{0,}\\]");
            Matcher m = p.matcher(type);
            while (m.find()) {
                String s = m.group();
                String dig = s.substring(s.indexOf('[') + 1, s.indexOf(']')).trim();
                if (dig.isEmpty()) {
                    dimensions.add(0);
                } else {
                    dimensions.add(Integer.valueOf(dig));
                }
            }
        }

        @Override
        public String toString() {
            return "Type{"
                    + "name='"
                    + type
                    + '\''
                    + ", baseName='"
                    + rawType
                    + '\''
                    + ", dimensions="
                    + dimensions
                    + '}';
        }

        public String getType() {
            return type;
        }

        public String getRawType() {
            return rawType;
        }

        public Type reduceDimensionAndGetType() {
            if (isList()) {
                String r = rawType;
                for (int i = 0; i < dimensions.size() - 1; i++) {
                    r += ("[" + (dimensions.get(i) != 0 ? dimensions.get(i) : "") + "]");
                }

                return new Type(r);
            }

            return new Type(rawType);
        }

        public boolean isList() {
            return !dimensions.isEmpty();
        }

        public boolean isDynamicList() {
            return isList() && (dimensions.get(dimensions.size() - 1) == 0);
        }

        public boolean isFixedList() {
            return isList() && (dimensions.get(dimensions.size() - 1) != 0);
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRawType(String rawType) {
            this.rawType = rawType;
        }

        public List<Integer> getDimensions() {
            return dimensions;
        }

        public Integer getLastDimension() {
            if (!isList()) {
                return 0;
            }

            return dimensions.get(dimensions.size() - 1);
        }

        public void setDimensions(List<Integer> dimensions) {
            this.dimensions = dimensions;
        }
    }

    public static class NamedType {
        private String name;
        private String type;
        private boolean indexed;
        private List<NamedType> components;

        public NamedType() {}

        public NamedType(String name, String type) {
            this(name, type, false);
        }

        public NamedType(String name, String type, boolean indexed) {
            this.name = name;
            this.type = type;
            this.indexed = indexed;
        }

        public Type newType() {
            return new Type(type);
        }

        private String getTupleRawTypeAsString() {
            StringBuilder result = new StringBuilder();
            String params =
                    getComponents()
                            .stream()
                            .map(abi -> abi.getTypeAsString())
                            .collect(Collectors.joining(","));
            result.append(params);
            return result.toString();
        }

        public String getTypeAsString() {
            // not tuple, return
            if (!type.startsWith("tuple")) {
                return type;
            }

            String tupleRawString = getTupleRawTypeAsString();
            String result = type.replaceAll("tuple", "(" + tupleRawString + ")");
            return result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isIndexed() {
            return indexed;
        }

        public void setIndexed(boolean indexed) {
            this.indexed = indexed;
        }

        public List<NamedType> getComponents() {
            return components;
        }

        public void setComponents(List<NamedType> components) {
            this.components = components;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NamedType namedType = (NamedType) o;
            return indexed == namedType.indexed
                    && Objects.equals(name, namedType.name)
                    && Objects.equals(type, namedType.type)
                    && Objects.equals(components, namedType.components);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type, indexed, components);
        }

        @Override
        public String toString() {
            return "NamedType{"
                    + "name='"
                    + name
                    + '\''
                    + ", type='"
                    + type
                    + '\''
                    + ", indexed="
                    + indexed
                    + ", components="
                    + components
                    + '}';
        }
    }
}
