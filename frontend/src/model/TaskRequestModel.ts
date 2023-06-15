import { TagModel } from "./TagModel"

export type TaskRequestModel = {
  user: string,
  name: string,
  description: string,
  timestamp: string,
  tags: TagModel[]
}
