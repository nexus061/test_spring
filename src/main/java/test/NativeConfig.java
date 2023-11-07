package test;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import test.NativeConfig.ValidationRuntimesHints;
import test.model.RequestDTO;

@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints({ ValidationRuntimesHints.class })
public class NativeConfig {

	public NativeConfig() {
		
	}

	static class ValidationRuntimesHints implements RuntimeHintsRegistrar {

		public ValidationRuntimesHints() {
			super();
		}

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			
		//	hints.reflection().registerType(RequestDTO.class, MemberCategory.values());

		}

	}
	
 
}
