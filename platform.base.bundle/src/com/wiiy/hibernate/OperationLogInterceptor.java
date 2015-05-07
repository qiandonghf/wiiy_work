package com.wiiy.hibernate;

/*import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.service.HttpSessionService;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
*/
public class OperationLogInterceptor {
/*public class OperationLogInterceptor extends EmptyInterceptor {
	
	private HttpSessionService httpSessionService;
	
	private Map<String, PropertyValueGetter> propertyValueGettersOnSave = new HashMap<String, PropertyValueGetter>();

	private Map<String, PropertyValueGetter> propertyValueGettersOnUpdate = new HashMap<String, PropertyValueGetter>();
	
	public OperationLogInterceptor() {
		
		PropertyValueGetter realnameGetter = new PropertyValueGetter() {
			@Override
			public Object get() {
				return httpSessionService.getSessionUser().getRealName();
			}
		};
		PropertyValueGetter idGetter = new PropertyValueGetter() {
			@Override
			public Object get() {
				return httpSessionService.getSessionUser().getId();
			}
		};
		PropertyValueGetter dateGetter = new PropertyValueGetter() {
			@Override
			public Object get() {
				return new Date();
			}
		};

		propertyValueGettersOnSave.put("creator", realnameGetter);
		propertyValueGettersOnSave.put("creatorId", idGetter);
		propertyValueGettersOnSave.put("createTime", dateGetter);
		propertyValueGettersOnSave.put("modifier", realnameGetter);
		propertyValueGettersOnSave.put("modifierId", idGetter);
		propertyValueGettersOnSave.put("modifyTime", dateGetter);
		
		propertyValueGettersOnUpdate.put("modifier", realnameGetter);
		propertyValueGettersOnUpdate.put("modifierId", idGetter);
		propertyValueGettersOnUpdate.put("modifyTime", dateGetter);
	}
	
    public void setHttpSessionService(HttpSessionService httpSessionService) {
		this.httpSessionService = httpSessionService;
	}

	public boolean onSave(
            Object entity, Serializable id, final Object[] state, String[] propertyNames, Type[] types) {

        return setProperties(entity, state, propertyNames, propertyValueGettersOnSave);
    }

	private boolean setProperties(Object entity, final Object[] state,
			String[] propertyNames, Map<String, PropertyValueGetter> propertyValueGetters) {
		if (!(entity instanceof BaseEntity)) {
            return false;
        }

        boolean updated = false;

        for (int i = 0; i < propertyNames.length; i++) {
        	
        	PropertyValueGetter propertyValueGetter = propertyValueGetters.get(propertyNames[i]);
        	
        	if (propertyValueGetter != null) {
                state[i] = propertyValueGetter.get();
                updated = true;
            }
        }
	
        return updated;
	}

    public boolean onFlushDirty(
            Object entity, Serializable id, final Object[] currentState,
            final Object[] previousState, String[] propertyNames, Type[] types) {

        return setProperties(entity, currentState, propertyNames, propertyValueGettersOnUpdate);
    }

    interface PropertyValueGetter {
    	Object get();
    }
*/}
