package org.fisco.bcos.web3j.abi.datatypes;

import java.util.List;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.Utils;

/** Function type. */
public class Function {
  private String name;
  private List<Type> inputParameters;
  private List<TypeReference<Type>> outputParameters;

  public Function(
      String name, List<Type> inputParameters, List<TypeReference<?>> outputParameters) {
    this.name = name;
    this.inputParameters = inputParameters;
    this.outputParameters = Utils.convert(outputParameters);
  }

  public String getName() {
    return name;
  }

  public List<Type> getInputParameters() {
    return inputParameters;
  }

  public List<TypeReference<Type>> getOutputParameters() {
    return outputParameters;
  }
}
