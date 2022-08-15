package Error

type MyError struct {
}

func (err *MyError) Error() string {
	return "this is MyError"
}
