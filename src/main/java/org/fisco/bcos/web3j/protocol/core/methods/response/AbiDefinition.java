package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** AbiDefinition wrapper. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbiDefinition {
    private boolean constant;
    private List<NamedType> inputs;
    private String name;
    private List<NamedType> outputs;
    private String type;
    private boolean payable;

    private String stateMutability;

    public static List<String> CONSTANT_KEY = Arrays.asList("view");

    public AbiDefinition() {}

    public AbiDefinition(
            boolean constant,
            List<NamedType> inputs,
            String name,
            List<NamedType> outputs,
            String type,
            boolean payable) {
        this(constant, inputs, name, outputs, type, payable, null);
    }

    public AbiDefinition(
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

    public boolean isConstant() {
        return constant || CONSTANT_KEY.contains(this.getStateMutability());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbiDefinition)) {
            return false;
        }

        AbiDefinition that = (AbiDefinition) o;

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

    public static class NamedType {

        private String name;
        private String type;
        private Type type0;
        private boolean indexed;

        public NamedType() {}

        public NamedType(String name, String type) {
            this.name = name;
            this.type = type;
            this.setType0(new Type(name));
        }

        public NamedType(String name, String type, boolean indexed) {
            this.name = name;
            this.type = type;
            this.indexed = indexed;
            this.setType0(new Type(name));
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof NamedType)) {
                return false;
            }

            NamedType namedType = (NamedType) o;

            if (isIndexed() != namedType.isIndexed()) {
                return false;
            }

            if (getName() != null
                    ? !getName().equals(namedType.getName())
                    : namedType.getName() != null) {
                return false;
            }
            return getType() != null
                    ? getType().equals(namedType.getType())
                    : namedType.getType() == null;
        }

        @Override
        public int hashCode() {
            int result = getName() != null ? getName().hashCode() : 0;
            result = 31 * result + (getType() != null ? getType().hashCode() : 0);
            result = 31 * result + (isIndexed() ? 1 : 0);
            return result;
        }

        public Type getType0() {
            return type0;
        }

        public void setType0(Type type0) {
            this.type0 = type0;
        }

        public static class Type {
            public String name;
            public String baseName;
            public List<Integer> depth = new ArrayList<Integer>();

            public Type(String name) {
                int index = name.indexOf("[");
                this.baseName = (-1 == index) ? name.trim() : name.substring(0, index);

                this.name = name;
                this.doParser();
            }

            private void doParser() {
                Pattern p = Pattern.compile("\\[[0-9]{0,}\\]");
                Matcher m = p.matcher(name);
                while (m.find()) {
                    String s = m.group();
                    String dig = s.substring(s.indexOf("[") + 1, s.indexOf("]")).trim();
                    if (dig.isEmpty()) {
                        depth.add(new Integer(0));
                    } else {
                        depth.add(Integer.valueOf(dig));
                    }
                }
            }

            @Override
            public String toString() {
                return "Type [name=" + name + ", baseName=" + baseName + ", depth=" + depth + "]";
            }

            public String getName() {
                return name;
            }

            public int getDimensions() {
                if (arrayType()) {
                    return depth.get(depth.size() - 1);
                }
                return 0;
            }

            public String getBaseName() {
                return baseName;
            }

            public boolean arrayType() {
                return 0 != getDepth();
            }

            public boolean staticArray() {
                return arrayType() && (depth.get(depth.size() - 1) > 0);
            }

            public boolean dynamicArray() {
                return arrayType() && (depth.get(depth.size() - 1) == 0);
            }

            public int getDepth() {
                return depth.size();
            }

            public List<Integer> getDepthArray() {
                return depth;
            }
        }
    }
}
