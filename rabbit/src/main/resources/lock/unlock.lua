local lockKey = KEYS[1]
local lockValue = KEYS[2]

-- get key
local result_1 = redis.call('get', lockKey)
if result_1 == lockValue
then
    local result_2= redis.call('del', lockKey)
    if result_2
    then
        return 'ok'
    else
        return 'failed'
    end
else
    return 'failed'
end
