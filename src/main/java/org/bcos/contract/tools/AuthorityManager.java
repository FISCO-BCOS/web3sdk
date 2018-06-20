package org.bcos.contract.tools;


public class AuthorityManager {
	
	public static void main(String[] args) throws Exception {
		
		if(args.length<1){
			System.out.println("The parameter can not be empty, at least 1 parameter is needed!");
			return; 
		}
		AuthorityManagerTools authorityManagerTools=new AuthorityManagerTools();
		boolean configure = authorityManagerTools.loadConfig();
		if (!configure) {
			System.err.println("error in load configure, init failed !");
			return;
		}
	    switch (args[0]) {
	    	case "PermissionInfo":
	    		int filtersLength=authorityManagerTools.getInfo();
	    	    System.out.println("The number of filters on the chain:" + filtersLength);
	    	    break;
	    	case "FilterChain":
	    		switch (args[1]) {
	    		    case "addFilter":
	    		    	if(args.length!=5){
	    		    		System.out.println("please input:FilterChain addFilter name1 version1 desc1");
	    		    	}else{
	    		    		System.out.println("Start the addFilter operation：");
	    		    		Boolean flag=authorityManagerTools.addFilter(args[2], args[3], args[4]);
	    		    		if (flag) {
	    		    			System.out.println("Newly Added Filter Authority Control Has Started");
							}else {
								System.out.println("Newly Added Filter Authority Control Has Not Started");
							}
	    		    	}
	    		    	break;
	    		    case "delFilter":
	    		    	if(args.length!=3){
	    		    		System.out.println("please input：FilterChain delFilter 0");
	    		    	}else{
	    		    		int delFilter=authorityManagerTools.delFilter(args[2]);
	    		    		if(delFilter==0){
	    		    			System.out.println("delFilter false");
	    		    		}else{
	    		    			System.out.println("delFilter success");
	    		    		}
	    		    	}
	    		    	break;
	    		    case "showFilter":
	    		    	authorityManagerTools.showFilter();
	    		    	break;
	    		    case "resetFilter":	
	    		    	authorityManagerTools.resetFilter();
	    		    	break;
	    		    default:
	    		    	System.out.println("Parameter error!");
						break;	
	    		}
	    		break;
	    	case "Filter":
	    		switch (args[1]) {
				case "getFilterStatus":
					if(args.length!=3){
						System.out.println("please input:Filter getFilterStatus 0");
					}else{
						Boolean authorityFlag=authorityManagerTools.getFilterStatus(args[2]);
						if(authorityFlag){
							System.out.println("The Filter permission control has started");
						}else{
							System.out.println("The Filter permission controls the off state");
						}
					}
					break;
				case "enableFilter":
					if(args.length!=3){
						System.out.println("please input:Filter enableFilter 0");
					}else{
						Boolean authorityFlag=authorityManagerTools.enableFilter(args[2]);
						if(authorityFlag){
							System.out.println("The Filter permission control has started");
						}else{
							System.out.println("The Filter permission controls the off state");
						}
					}
					break;	
				case "disableFilter":
					if(args.length!=3){
						System.out.println("please input:Filter disableFilter 0");
					}else{
						Boolean authorityFlag=authorityManagerTools.disableFilter(args[2]);
						if(authorityFlag){
							System.out.println("The Filter permission control has started");
						}else{
							System.out.println("The Filter permission controls the off state");
						}
					}
					break;
				case "setUsertoNewGroup":
					if(args.length!=4){
						System.out.println("please input:Filter setUsertoNewGroup 0 account");
					}else{
						authorityManagerTools.setUsertoNewGroup(args[2], args[3]);
					}
					break;
				case "setUsertoExistingGroup":	
					if(args.length!=5){
						System.out.println("please input:Filter setUsertoExistingGroup 0 account group");
					}else{
						authorityManagerTools.setUsertoExistingGroup(args[2], args[3], args[4]);
					}
					break;
				case "listUserGroup":	
					if(args.length!=4){
						System.out.println("please input:Filter listUserGroup 0 account");
					}else{
						String user=authorityManagerTools.listUserGroup(args[2], args[3]);
						if("undefined".equals(user)){
							System.out.println("This account has no role set");
						}else{
							System.out.println("The role of this account is："+user);
						}
					}
					break;
				default:
					System.out.println("Parameter error!");
					break;
				}
	    		break;
	    	case "Group":
	            switch (args[1]) {
				case "getBlackStatus":
					if(args.length!=4){
						System.out.println("please input:Group getBlackStatus 0 account");
					}else{
						Boolean blackFlag=authorityManagerTools.getBlackStatus(args[2], args[3]);
						if(blackFlag){
							System.out.println("This group blacklist mode is enabled");
						}else{
							System.out.println("The Group blacklist model has not been enabled");
						}
					}
					break;
				case "enableBlack":
					if(args.length!=4){
						System.out.println("please input:Group enableBlack 0 account");
					}else{
						Boolean blackFlag=authorityManagerTools.enableBlack(args[2], args[3]);
						if(blackFlag){
							System.out.println("This group blacklist mode is enabled");
						}else{
							System.out.println("The Group blacklist model has not been enabled");
						}
					}
					break;
				case "disableBlack":
					if(args.length!=4){
						System.out.println("please input:Group disableBlack 0 account");
					}else{
						Boolean blackFlag=authorityManagerTools.disableBlack(args[2], args[3]);
						if(blackFlag){
							System.out.println("This group blacklist mode is enabled");
						}else{
							System.out.println("The Group blacklist model has not been enabled");
						}
					}
					break;
				case "getDeployStatus":
					if(args.length!=4){
						System.out.println("please input:Group getDeployStatus 0 account");
					}else{
						Boolean groupCreate=authorityManagerTools.getDeployStatus(args[2], args[3]);
						if(groupCreate){
							System.out.println("The Group publishes contract functionality is enabled");
						}else{
							System.out.println("The Group publish contract feature has not been enabled");
						}
					}
					break;
				case "enableDeploy":
					if(args.length!=4){
						System.out.println("please input:Group enableDeploy 0 account");
					}else{
						Boolean groupCreate=authorityManagerTools.enableDeploy(args[2], args[3]);
						if(groupCreate){
							System.out.println("The Group publishes contract functionality is enabled");
						}else{
							System.out.println("The Group publish contract feature has not been enabled");
						}
					}
					break;
				case "disableDeploy":
					if(args.length!=4){
						System.out.println("please input:Group disableDeploy 0 account");
					}else{
						Boolean groupCreate=authorityManagerTools.disableDeploy(args[2], args[3]);
						if(groupCreate){
							System.out.println("The Group publishes contract functionality is enabled");
						}else{
							System.out.println("The Group publish contract feature has not been enabled");
						}
					}
					break;
				case "addPermission":
					if(args.length!=6){
						System.out.println("please input:Group addPermission 0 account A.address fun(string)");
					}else{
						authorityManagerTools.addPermission(args[2],args[3],args[4],args[5]);
					}
					break;
				case "delPermission":
					if(args.length!=6){
						System.out.println("please input:Group delPermission 0 account A.address fun(string)");
					}else{
						authorityManagerTools.delPermission(args[2],args[3],args[4],args[5]);
					}
					break;
				case "checkPermission":
					if(args.length!=6){
						System.out.println("please input:Group checkPermission 0 account A.address fun(string)");
					}else{
						Boolean flag=authorityManagerTools.checkPermission(args[2],args[3],args[4],args[5]);
						System.out.println("Permission is："+flag);
					}
					break;
				case "listPermission":
					if(args.length!=4){
						System.out.println("please input:Group listPermission 0 account");
					}else{
						authorityManagerTools.listPermission(args[2],args[3]);
					}
					break;
				default:
					System.out.println("Parameter error!");
					break;
				}
	    		
	    		break;
	    	default:
	    		System.out.println("Parameter error!");
                break;	
	    }
	    
	    System.exit(0);
	}
	    
}
